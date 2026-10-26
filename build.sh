#!/usr/bin/env bash
set -e

echo "=== 1. Running tests and building jlink image + zip ==="
mvn clean test javafx:jlink

cp src/main/resources/com/rnd/view/rlsc_icon.png target/rlsc-image/rlsc.png
zip -j target/rlsc-linux-bin.zip target/rlsc-image/rlsc.png

echo "=== 2. Preparing AppDir for AppImage ==="
APPDIR="target/RLSC.AppDir"
rm -rf "$APPDIR"
mkdir -p "$APPDIR/usr"

# Copy jlink runtime image into AppDir/usr
cp -r target/rlsc-image/* "$APPDIR/usr/"
cp target/rlsc-image/rlsc.png "$APPDIR/rlsc.png"

# Create AppRun entry point
cat << 'EOF' > "$APPDIR/AppRun"
#!/usr/bin/env bash
HERE="$(dirname "$(readlink -f "${0}")")"
exec "$HERE/usr/bin/rlsc" "$@"
EOF
chmod +x "$APPDIR/AppRun"

# Create .desktop file required by AppImage
cat << 'EOF' > "$APPDIR/rlsc.desktop"
[Desktop Entry]
Type=Application
Name=RLSC
Comment=RLinux Shortcut Creator
Exec=rlsc
Icon=rlsc
Categories=Utility;
Terminal=false
StartupNotify=true
EOF

if command -v appimagetool >/dev/null 2>&1; then
    echo "=== 3. Building RLSC-x86_64.AppImage ==="
    ARCH=x86_64 appimagetool "$APPDIR" target/RLSC-x86_64.AppImage
    chmod +x target/RLSC-x86_64.AppImage
    echo ""
    echo "=== Build Complete! ==="
    echo "-> Binary Zip (for AUR rlsc-bin): target/rlsc-bin-linux.zip"
    echo "-> AppImage (Universal Linux):    target/RLSC-x86_64.AppImage"
else
    echo ""
    echo "=== jlink Build Complete! ==="
    echo "-> Binary Zip (for AUR rlsc-bin): target/rlsc-linux-bin.zip"
    echo "-> Standalone folder:             target/rlsc-image/bin/rlsc"
    echo "(!) To also generate the .AppImage file, install appimagetool (sudo pacman -S appimagetool) and run ./build.sh again."
fi
