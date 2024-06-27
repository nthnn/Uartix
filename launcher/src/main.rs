/*
 * This file is part of the Uartix programming language (https://github.com/nthnn/Uartix).
 * Copyright (c) 2024 Nathanne Isip.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

use std::env;
use std::fs;
use std::process;
use std::process::Command;

fn main() {
    let args: Vec<String> = env::args().skip(1).collect();
    let jar: &str = "/opt/uartix/Uartix.jar";

    if !fs::metadata(jar).is_ok() {
        println!("\u{001b}[31mError\u{001b}[0m: /opt/uartix/Uartix.jar file not found.");
        process::exit(-1);
    }

    Command::new("java")
        .arg("-jar")
        .arg(jar)
        .args(&args)
        .status()
        .expect("Failed to execute JAR file");
}
