<p align="center">
    <img src="logo/uartix-round.png" width="162" />
</p>

<h1 align="center">Uartix</h1>

![Build CI](https://github.com/nthnn/Uartix/actions/workflows/build_ci.yml/badge.svg)
![Picoware CI](https://github.com/nthnn/Uartix/actions/workflows/picoware_ci.yml/badge.svg)
![Launcher Build CI](https://github.com/nthnn/Uartix/actions/workflows/launcher_build_ci.yml/badge.svg)
[![GitHub Issues](https://img.shields.io/github/issues/nthnn/Uartix.svg)](https://github.com/nthnn/Uartix/issues)
[![GitHub Stars](https://img.shields.io/github/stars/nthnn/Uartix.svg)](https://github.com/nthnn/Uartix/stargazers)
![GitHub repo size](https://img.shields.io/github/repo-size/nthnn/Uartix?logo=git&label=Repository%20Size)
[![License](https://img.shields.io/badge/license-GNU%20GPL%20v3-blue.svg)](https://github.com/nthnn/Uartix/blob/main/LICENSE)

A strange dynamic programming language that performs mathematical computations on a connected Raspberry Pi Pico through UART connectivity.

```utx
# Hello world example

greet = func(name)
    render "Hello, " + name;

greet("world");
```

See the complete documentation [here](https://uartix.vercel.app).

## Why Uartix?

Despite its unconventional and dynamic behavior, as well as its lack of fundamental APIs, Uartix can still serve as a general-purpose programming and scripting language. The following points outline the reasons behind the development of Uartix:

- The Raspberry Pi Pico, with its dual-core ARM Cortex-M0+ processor, offers an irrelevant yet affordable platform for hardware-level computation.
- Uartix provides a specialized environment where developers can perform mathematical operations directly on the hardware.
- Compared to many external coprocessors, Uartix running on a Raspberry Pi Pico presents a highly cost-effective solution. The affordability of the Pico reduces overall project costs while still delivering substantial computational power for a variety of applications.
- The Raspberry Pi Pico, with its energy-efficient ARM Cortex-M0+ cores, offers a low-power solution for performing mathematical calculations which is perfect for Uartix runtime execution.
- Why not? I was bored when I started developing this.

## Getting Started

Before installing Uartix, make sure you have JDK 22 (or OpenJDK) installed on your system. Follow the steps below to get started on different operating systems and to build various components from the source.

### Installing Uartix

#### Linux

1. **Download the `.deb` File**: Visit the [release page](https://github.com/nthnn/Uartix/releases) and download the latest `*.deb` file for Uartix.
2. **Install Uartix**: Open your terminal and navigate to the directory where the `.deb` file is located. Run the following command to install Uartix:

   ```shell
   sudo dpkg -i uartix_*.deb
   ```

3. **Running Uartix**: After successfully install the `.deb` package, you can now run the command `uartix` on your terminal.

#### Windows

1. **Download the .zip File**: Go to the [release page](https://github.com/nthnn/Uartix/releases) and download the latest `.zip` file for Uartix.

2. **Extract the File**: Extract the contents of the `.zip` file to `C:\uartix`.

3. **Set Environment Path**: Add `C:\uartix\bin` to your Environment Path variables to ensure you can run Uartix from any command prompt.

#### Firmware Installation

To install the Uartix firmware on your Raspberry Pi Pico, follow these steps:

1. **Enter Flash Mode**: Connect your Raspberry Pi Pico to your system while holding the `BOOTSEL` button to enter flash mode.

2. **Download the UF2 Binary**: Download the UF2 binary of the Uartix firmware from the [release page](https://github.com/nthnn/Uartix/releases).

3. **Install the Firmware**: Drag and drop the downloaded UF2 file into the Raspberry Pi Pico storage that appears on your computer.

### Building from Source

#### Interpreter

To build the interpreter:

1. **Open in IntelliJ**: Open the Uartix repository in IntelliJ IDEA.
2. **Build Artifacts**: From the menu, go to `Build` menu item and select `Build Artifacts > Build`.

#### Launcher

On Ubuntu, to build the Uartix launcher, ensure you have Rust and cargo installed on your system. Follow these steps:

1. **Install Dependencies**:

   ```shell
   sudo apt-get install mingw-w64
   rustup target add x86_64-pc-windows-gnu
   ```

2. **Build the Launcher**: Run the following commands to build the launcher:

   ```shell
   cargo build --release
   cargo build --release --target x86_64-pc-windows-gnu
   ```

#### Firmware

To build the Uartix firmware from source, simply follow the steps below.

1. **Installing Raspberry Pi Pico on Arduino IDE**: Install the Raspberry Pi Pico boards on your Arduino IDE by following the steps [here](https://randomnerdtutorials.com/programming-raspberry-pi-pico-w-arduino-ide/).
2. **Open in Arduino IDE:**: Open the file [launcher/picoware.ino](launcher/picoware.ino) in your Arduino IDE.
3. **Build & Upload**: Connect your Raspberry Pi Pico board on flash mode then upload and build the Picoware on your Arduino IDE.

## Development Support

Uartix is an open-source project and is voluntarily developed. If you find Uartix useful and would like to support its continued development and improvement, you can make a donation.

<p align="center">
    <a href="https://opencollective.com/nathanne-isip">
        <img src="https://opencollective.com/webpack/donate/button@2x.png?color=blue" width="250" />
    </a>
</p>

## Contributing and Contributors

All contributions are welcome to make Uartix even better. Whether you want to report a bug, suggest new features, or contribute code, your contributions are highly appreciated.

### Issue Reporting

If you come across a bug, have a feature request, or wish to propose enhancements, we kindly encourage you to initiate the process by opening an issue on our GitHub [Issue Tracker](https://github.com/nthnn/Uartix/issues). Your feedback is invaluable in helping us refine and enhance the Uartix. To ensure we can address your concern effectively, we kindly request that you include as much pertinent information as possible. This may encompass a detailed description of the issue or feature request, and if applicable, specific steps to replicate the problem.

Your thorough input enables us to better understand and resolve the matter swiftly, contributing to the overall improvement of Uartix. Thank you for your contributions and for helping us create a more robust and user-friendly environment.

### Pull Requests

If you're eager to get involved and contribute your coding expertise to Uartix, we're thrilled to have you on board! To ensure a smooth and collaborative process, here's an outlined the following steps that you can follow:

1. Fork the Uartix repository to your GitHub account. And then clone it to your local machine.

    ```shell
    git clone https://github.com/<your username>/Uartix.git
    ```

2. Create a new branch for your changes:

    ```shell
    git checkout -b feature/<your feature name>
    ```

3. You can now make changes to the repository.

4. Commit your changes:

    ```shell
    git add -A
    git commit -m "Add your meaningful commit message here"
    ```

5. Push your changes to your forked repository:

    ```shell
    git push origin feature/<your feature name>
    ```

6. Create a pull request (PR) from your branch to the main branch of the Uartix repository.

7. Your PR will be reviewed, and any necessary changes will be discussed and implemented.

8. Once your PR is approved, it will be merged into the main branch, and your contribution will be part of Uartix.

### Contributors

Finally, this section acknowledges and celebrates the individuals who have made significant contributions to the development and success of the project. Meet the dedicated and talented team members, developers, and collaborators who have played key roles in bringing Uartix to life and advancing its objectives.

- [Nathanne Isip](https://github.com/nthnn) — Original Author, Developer
- [Lady Selene](https://instagram.com/lady.selenee) — Artist, Illustrator


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
