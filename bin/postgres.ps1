$ErrorActionPreference = 'Stop'

if ([string]::IsNullOrWhiteSpace($env:POSTGRES_PASSWORD)) {
    throw 'Set POSTGRES_PASSWORD before running this script.'
}

$postgresImage = if ($env:POSTGRES_IMAGE) { $env:POSTGRES_IMAGE } else { 'postgres:latest' }
$containerName = if ($env:POSTGRES_CONTAINER_NAME) { $env:POSTGRES_CONTAINER_NAME } else { 'fb-post-search-postgres' }
$postgresDatabase = if ($env:POSTGRES_DB) { $env:POSTGRES_DB } else { 'fb_post_search' }
$postgresUser = if ($env:POSTGRES_USER) { $env:POSTGRES_USER } else { 'postgres' }
$postgresPort = if ($env:POSTGRES_PORT) { $env:POSTGRES_PORT } else { '5432' }

& docker pull $postgresImage
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

$dockerArguments = @(
    'run',
    '--rm',
    '--name', $containerName,
    '--env', "POSTGRES_DB=$postgresDatabase",
    '--env', "POSTGRES_USER=$postgresUser",
    '--env', "POSTGRES_PASSWORD=$env:POSTGRES_PASSWORD",
    '--publish', "${postgresPort}:5432",
    $postgresImage
)

& docker @dockerArguments
exit $LASTEXITCODE
