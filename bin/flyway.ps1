$ErrorActionPreference = 'Stop'

if ([string]::IsNullOrWhiteSpace($env:POSTGRES_PASSWORD)) {
    throw 'Set POSTGRES_PASSWORD before running this script.'
}

$projectRoot = Split-Path -Parent $PSScriptRoot
$migrationsDirectory = Join-Path $projectRoot 'flyway'
$flywayImage = if ($env:FLYWAY_IMAGE) { $env:FLYWAY_IMAGE } else { 'flyway/flyway:latest' }
$postgresHost = if ($env:POSTGRES_HOST) { $env:POSTGRES_HOST } else { 'host.docker.internal' }
$postgresPort = if ($env:POSTGRES_PORT) { $env:POSTGRES_PORT } else { '5432' }
$postgresDatabase = if ($env:POSTGRES_DB) { $env:POSTGRES_DB } else { 'fb_post_search' }
$postgresUser = if ($env:POSTGRES_USER) { $env:POSTGRES_USER } else { 'postgres' }

& docker pull $flywayImage
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

$dockerArguments = @(
    'run',
    '--rm',
    '--volume', "${migrationsDirectory}:/flyway/sql:ro",
    $flywayImage,
    "-url=jdbc:postgresql://${postgresHost}:${postgresPort}/${postgresDatabase}",
    "-user=$postgresUser",
    "-password=$env:POSTGRES_PASSWORD",
    'migrate'
)

& docker @dockerArguments
exit $LASTEXITCODE
