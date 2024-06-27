<p align="center">
    <img src="logo/uartix-round.png" width="162" />
</p>

<h1 align="center">Uartix</h1>

![Build CI](https://github.com/nthnn/Uartix/actions/workflows/build_ci.yml/badge.svg)
![Picoware CI](https://github.com/nthnn/Uartix/actions/workflows/picoware_ci.yml/badge.svg)
![Launcher Build CI](https://github.com/nthnn/Uartix/actions/workflows/launcher_build_ci.yml/badge.svg)

A strange dynamic programming language that performs mathematical computations on a connected Raspberry Pi Pico through UART connectivity.

```utx
# Hello world example

greet = func(name)
    render "Hello, " + name;

greet("world");
```

See the complete documentation [here](https://uartix.vercel.app).

## Why?

- The Raspberry Pi Pico, with its dual-core ARM Cortex-M0+ processor, offers a powerful yet affordable platform for hardware-level computation.
- Uartix provides a specialized environment where developers can perform complex mathematical operations directly on the hardware.
- On a Raspberry Pi Pico, Uartix offers a cost-effective solution compared to many external coprocessors.
- Uartix enhances the capabilities of the Raspberry Pi Pico by simplifying the development process with hardware-level mathematical calculations.
- The Raspberry Pi Pico, with its energy-efficient ARM Cortex-M0+ cores, offers a low-power solution for performing mathematical calculations which is perfect for Uartix runtime execution.
- Why not? I was bored when I started developing this.

## Getting Started

Before installing Uartix, ensure you have JDK 22 (or OpenJDK) installed on your system.

### Linux

On Ubuntu and other Linux distro with `dpkg`, you can simply download the `*.deb` file from [release page](https://github.com/nthnn/Uartix/releases) and install Uartix with the following command:

```shell
sudo dpkg -i uartix_*.deb
```

### Windows

Download the `.zip` file from the [release page](https://github.com/nthnn/Uartix/releases) for Windows. Extract it to `C:\` and add `C:\uartix\bin` to your Environment Path variables.

## Building from Source

To build Uartix from source on Ubuntu, run the following commands to automate the build process for `*.deb` and `*.zip` files, excluding the build process for the interpreter:

```shell
chmod +x ./build.sh
./build.sh
```

## Interpreter

To build the interpreter, open this repository in IntelliJ. From the menu, click `Build` and select `Build Artifacts > Build`.

## Launcher

To build the Uartix launcher, ensure you have Rust and cargo installed on your system. Also, install the MinGW and Rust toolchain for Windows target with the following commands:

```shell
sudo apt-get install mingw-w64
rustup target add x86_64-pc-windows-gnu
```

Then, build the launcher with:

```shell
cargo build --release
cargo build --release --target x86_64-pc-windows-gnu
```

## Firmware

To install the firmware on your Raspberry Pi Pico, connect the device to your system in flash mode. Download the UF2 binary of the Uartix firmware from the [release page](https://github.com/nthnn/Uartix/releases) and drag and drop it to your Raspberry Pi Pico storage.

## Development Support

Uartix is an open-source project and is voluntarily developed. If you find Uartix useful and would like to support its continued development and improvement, you can make a donation.

<p align="center">
    <a href="https://opencollective.com/nathanne-isip">
        <img src="https://opencollective.com/webpack/donate/button@2x.png?color=blue" width="250" />
    </a>
</p>

## License

[Uartix](https://github.com/nthnn/Uartix) programming language.
Copyright (c) 2024 Nathanne Isip. 

This program is free software: you can redistribute it and/or modify 
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see [http://www.gnu.org/licenses/](http://www.gnu.org/licenses/).
