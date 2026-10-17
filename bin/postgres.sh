#!/usr/bin/env bash

set -euo pipefail

: "${POSTGRES_PASSWORD:?Set POSTGRES_PASSWORD before running this script.}"

POSTGRES_IMAGE="${POSTGRES_IMAGE:-postgres:latest}"
POSTGRES_CONTAINER_NAME="${POSTGRES_CONTAINER_NAME:-fb-post-search-postgres}"
POSTGRES_DB="${POSTGRES_DB:-fb_post_search}"
POSTGRES_USER="${POSTGRES_USER:-postgres}"
POSTGRES_PORT="${POSTGRES_PORT:-5432}"

docker pull "$POSTGRES_IMAGE"
docker run --rm --name "$POSTGRES_CONTAINER_NAME" \
  --env "POSTGRES_DB=$POSTGRES_DB" \
  --env "POSTGRES_USER=$POSTGRES_USER" \
  --env "POSTGRES_PASSWORD=$POSTGRES_PASSWORD" \
  --publish "$POSTGRES_PORT:5432" \
  "$POSTGRES_IMAGE"
