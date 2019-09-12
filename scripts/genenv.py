#!/usr/bin/env python3

import sys
import argparse


def parse_env(path):
    ret = {}

    with open(path) as f:
        data = f.read()
        for line in data.split('\n'):
            line = line.split('#')[0]
            line.strip()

            if len(line) <= 0:
                continue

            name, value = line.split('=', maxsplit=1)

            ret[name] = value

        return ret


def main():
    parser = argparse.ArgumentParser(description='generate HCGW2 environment.')
    parser.add_argument('OLDENV', help='old environment in text format')
    parser.add_argument('BASEENV', help='base uEnv.txt from yocto')
    args = parser.parse_args()

    env = parse_env(args.BASEENV)
    oldenv = parse_env(args.OLDENV)

    for name in list(oldenv):
        keep = False

        if name.startswith('conf_openvpn_'):
            keep = True
        if name == 'conf_mac':
            keep = True
        if name.startswith('hk-'):
            keep = True
        if name == 'gatewayid':
            keep = True
        if name == 'sgtin':
            keep = True

        if keep:
            env[name] = oldenv[name]

    # the following lines were adapted from mt7688's manufacturing tools
    env['board_name'] = 'smart-gateway-at91sam'
    env['ethaddr'] = [i for i in env['conf_mac'].split(';')
                      if i.startswith('eth0=')][0].split('=')[1]

    env['baudrate'] = '115200'
    env['bootslot'] = '0'

    for name in sorted(list(env)):
        print('%s=%s' % (name, env[name]))

    return 0


if __name__ == '__main__':
    sys.exit(main())
