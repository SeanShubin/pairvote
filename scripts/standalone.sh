#!/bin/bash
set -e

# Navigate to project root
cd "$(dirname "$0")/.."

echo "Building production bundle..."
./gradlew :composeApp:jsBrowserProductionWebpack

# Paths
JS_FILE="composeApp/build/kotlin-webpack/js/productionExecutable/composeApp.js"
CSS_FILE="composeApp/build/processedResources/js/main/styles.css"
OUTPUT_DIR="composeApp/build/dist"
OUTPUT_FILE="$OUTPUT_DIR/standalone.html"

echo "Creating standalone HTML file..."

# Create output directory if it doesn't exist
mkdir -p "$OUTPUT_DIR"

# Create standalone HTML with embedded CSS and JS
cat > "$OUTPUT_FILE" <<'HTML_START'
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>pairvote</title>
    <style>
HTML_START

# Embed CSS
cat "$CSS_FILE" >> "$OUTPUT_FILE"

cat >> "$OUTPUT_FILE" <<'HTML_MIDDLE'
    </style>
</head>
<body>
<div id="root"></div>
<script>
HTML_MIDDLE

# Embed JS
cat "$JS_FILE" >> "$OUTPUT_FILE"

cat >> "$OUTPUT_FILE" <<'HTML_END'
</script>
</body>
</html>
HTML_END

echo "✅ Standalone HTML created: $OUTPUT_FILE"
echo "   Size: $(du -h "$OUTPUT_FILE" | cut -f1)"
echo ""
echo "You can now open this file in a browser:"
echo "   file://$(pwd)/$OUTPUT_FILE"
