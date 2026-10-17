#!/usr/bin/env bash

set -euo pipefail

: "${POSTGRES_PASSWORD:?Set POSTGRES_PASSWORD before running this script.}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

FLYWAY_IMAGE="${FLYWAY_IMAGE:-flyway/flyway:latest}"
POSTGRES_HOST="${POSTGRES_HOST:-host.docker.internal}"
POSTGRES_PORT="${POSTGRES_PORT:-5432}"
POSTGRES_DB="${POSTGRES_DB:-fb_post_search}"
POSTGRES_USER="${POSTGRES_USER:-postgres}"

docker pull "$FLYWAY_IMAGE"
docker run --rm \
  --volume "$PROJECT_ROOT/flyway:/flyway/sql:ro" \
  "$FLYWAY_IMAGE" \
  "-url=jdbc:postgresql://$POSTGRES_HOST:$POSTGRES_PORT/$POSTGRES_DB" \
  "-user=$POSTGRES_USER" \
  "-password=$POSTGRES_PASSWORD" \
  migrate
