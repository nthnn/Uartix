rm -rf dist

mvn clean install
cd launcher && cargo build --release && cd ..

mkdir -p dist/uartix_0.1.0-1_amd64/opt/uartix
mkdir -p dist/uartix_0.1.0-1_amd64/usr/local/bin
mkdir -p dist/uartix_0.1.0-1_amd64/DEBIAN

cp out/artifacts/Uartix_jar/Uartix.jar dist/uartix_0.1.0-1_amd64/opt/uartix/
cp launcher/target/release/uartix dist/uartix_0.1.0-1_amd64/usr/local/bin/

touch dist/uartix_0.1.0-1_amd64/DEBIAN/control
echo "Package: uartix" > dist/uartix_0.1.0-1_amd64/DEBIAN/control
echo "Version: 0.1.0" >> dist/uartix_0.1.0-1_amd64/DEBIAN/control
echo "Architecture: amd64" >> dist/uartix_0.1.0-1_amd64/DEBIAN/control
echo "Maintainer: Nathanne Isip <nathanneisip@gmail.com>" >> dist/uartix_0.1.0-1_amd64/DEBIAN/control
echo "Description: Strange dynamic programming language that performs mathematical computations on a connected Raspberry Pi Pico through UART connectivity." >> dist/uartix_0.1.0-1_amd64/DEBIAN/control

cd dist
dpkg-deb --build uartix_0.1.0-1_amd64
cd ..
