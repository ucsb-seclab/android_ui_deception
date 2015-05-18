# REPOSITORY CONTENT #

This repository contains data and source code about the paper:

"What the App is That? Deception and Countermeasures in the Android User Interface",
presented at 2015 IEEE Symposium on Security and Privacy (SP)

This repository contains three main folders:

###paper
The pdf file of the paper.

###attacks
Source code and videos showing some of the attacks described in the paper.

###android_modifications
The source code of the Android modifications, enabling the proposed on-device, defense.

# HOW TO COMPILE AND USE THE MODIFIED ANDROID #


## Download the Android official source code
Download the Android source code (tag: `android-4.4_r1.2`) as explained [here](https://source.android.com/source/downloading.html).

Use:
```
repo  init -u https://android.googlesource.com/platform/manifest -b android-4.4_r1.2
```
instead of:
```
repo init -u https://android.googlesource.com/platform/manifest
```
to download the correct tag


## Apply the patches
Assume that `<git>` is the folder where this file (`README.md`) is and `<source>` is the folder where Android has been downloaded.

Run:
```
cd <git>/android_modifications
./apply_patches.sh <source>
```


## Compile the code
```
cd <source>
source build/envsetup.sh
lunch 2
make
```
Refer to [the official documentation](https://source.android.com/source/building-running.html) for more information.


## Run the emulator with the correct configuration

The patches have been tested on a device mimicking the appearance of a Nexus 4.

Use the following instructions to run the emulator in the correct configuration.

###Create a correct avd
Open Android AVD Manager 
```
cd <source>/sdk
android avd
``` 
and use it to create a new Nexus 4 avd (named `<avd_name>`).

###Link the avd to the built image
Assuming that `<avd_name>` has been created in `<avd_folder>`, run the following commands:
```
AVD=<avd_folder>
cd <source>
CWD=$(pwd)
ln -s "$CWD"/out/target/product/generic_x86/cache.img "$AVD"/cache.img
ln -s "$CWD"/out/target/product/generic_x86/userdata.img "$AVD"/userdata.img
ln -s "$CWD"/out/target/product/generic_x86/userdata-qemu.img "$AVD"/userdata-qemu.img
ln -s "$CWD"/prebuilts/qemu-kernel/x86/kernel-qemu "$AVD"/kernel-qemu
ln -s "$CWD"/out/target/product/generic_x86/ramdisk.img "$AVD"/ramdisk.img
ln -s "$CWD"/out/target/product/generic_x86/system.img "$AVD"/system.img
```

###Run the emulator
```
cd <source>
source build/envsetup.sh
emulator -avd <avd_name>
```

