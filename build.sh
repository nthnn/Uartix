rm -rf dist launcher/target

mkdir -p dist/uartix_0.1.1_amd64/opt/uartix
mkdir -p dist/uartix_0.1.1_amd64/usr/local/bin
mkdir -p dist/uartix_0.1.1_amd64/DEBIAN

cd launcher
cargo build --release
cargo build --release --target x86_64-pc-windows-gnu
cd ..

cp out/artifacts/Uartix_jar/Uartix.jar dist/uartix_0.1.1_amd64/opt/uartix/
cp -r examples dist/uartix_0.1.1_amd64/opt/uartix/
cp launcher/target/release/uartix dist/uartix_0.1.1_amd64/usr/local/bin/

touch dist/uartix_0.1.1_amd64/DEBIAN/control
echo "Package: uartix" > dist/uartix_0.1.1_amd64/DEBIAN/control
echo "Version: 0.1.1" >> dist/uartix_0.1.1_amd64/DEBIAN/control
echo "Architecture: amd64" >> dist/uartix_0.1.1_amd64/DEBIAN/control
echo "Maintainer: Nathanne Isip <nathanneisip@gmail.com>" >> dist/uartix_0.1.1_amd64/DEBIAN/control
echo "Description: Strange dynamic programming language that performs mathematical computations on a connected Raspberry Pi Pico through UART connectivity." >> dist/uartix_0.1.1_amd64/DEBIAN/control

cd dist
dpkg-deb --build uartix_0.1.1_amd64
rm -rf uartix_0.1.1_amd64
cd ..

mkdir -p dist/uartix_0.1.1_win_x86_64/bin
cp out/artifacts/Uartix_jar/Uartix.jar dist/uartix_0.1.1_win_x86_64/bin
cp launcher/target/x86_64-pc-windows-gnu/release/uartix.exe dist/uartix_0.1.1_win_x86_64/bin
cp -r examples dist/uartix_0.1.1_win_x86_64/examples
cd dist/uartix_0.1.1_win_x86_64
zip -5 -r ../uartix_0.1.1_win_x86_64.zip *

cd ../..
rm -rf dist/uartix_0.1.1_win_x86_64
