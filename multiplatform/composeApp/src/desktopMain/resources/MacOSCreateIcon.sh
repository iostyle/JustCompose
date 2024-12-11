# 将脚本与icon.png置于相同目录
# chmod +x .sh
# 执行
mkdir icons.iconset
sips -z 512 512 icon.png -o icons.iconset/icon_512x512.png
iconutil -c icns icons.iconset -o icon.icns
rm -rf ./icons.iconset