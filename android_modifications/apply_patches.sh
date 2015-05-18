#!/bin/bash

CWD=$(pwd)

cd "$1"/build/
git apply "$CWD"/build.patch
cd "$CWD"

cd "$1"/external/qemu
git apply "$CWD"/qemu.patch
cd "$CWD"

cd "$1"/frameworks/base
git apply "$CWD"/frameworks.patch
cd "$CWD"

cp -a ImageChooser "$1"/packages/apps/


