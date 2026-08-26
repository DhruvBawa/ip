#!/usr/bin/env bash
set -euo pipefail

# Keep UI test persistence isolated from the user's real task data.
mkdir -p target/ui-test-data
rm -f target/ui-test-data/data/larry.txt
cd target/ui-test-data
exec java -Dlarry.currentDate=2026-08-26 -cp ../classes larry.Larry
