# PostgreSQL Development Database

`bin/postgres.sh` and `bin/postgres.ps1` start the current `postgres:latest`
Docker image for local development. The container is removed when it stops;
its data is therefore not persisted between runs.

## Start the database

From Git Bash, WSL, or another Bash-compatible terminal, run:

```bash
POSTGRES_PASSWORD=change-me bash bin/postgres.sh
```

From PowerShell, run:

```powershell
$env:POSTGRES_PASSWORD = 'change-me'
.\bin\postgres.ps1
```

The database is exposed at `localhost:5432` with these defaults:

| Setting | Default |
| --- | --- |
| Database | `fb_post_search` |
| User | `postgres` |
| Image | `postgres:latest` |
| Container name | `fb-post-search-postgres` |

`POSTGRES_PASSWORD` is required. Do not use a development password outside a
local environment.

## Customize settings

```bash
POSTGRES_PASSWORD=change-me \
POSTGRES_DB=my_posts \
POSTGRES_USER=my_user \
POSTGRES_PORT=5433 \
bash bin/postgres.sh
```

In PowerShell, set the environment variables before starting the script:

```powershell
$env:POSTGRES_PASSWORD = 'change-me'
$env:POSTGRES_DB = 'my_posts'
$env:POSTGRES_USER = 'my_user'
$env:POSTGRES_PORT = '5433'
.\bin\postgres.ps1
```

Both scripts also accept `POSTGRES_IMAGE` and `POSTGRES_CONTAINER_NAME` to
override the Docker image and container name.

The matching JDBC URL is:

```text
jdbc:postgresql://localhost:5432/fb_post_search
```

Use the selected values when any of the optional environment variables above
are overridden.

## Apply database migrations

The versioned SQL migrations are stored in `flyway/`. Start PostgreSQL first,
then run the Flyway container.

From PowerShell on Windows:

```powershell
$env:POSTGRES_PASSWORD = 'change-me'
.\bin\flyway.ps1
```

From Git Bash, WSL, or another Bash-compatible terminal:

```bash
POSTGRES_PASSWORD=change-me bash bin/flyway.sh
```

`bin/flyway.sh` runs `flyway/flyway:latest` and applies pending migrations to
the PostgreSQL instance. It connects to `host.docker.internal` by default,
which reaches the Docker-published PostgreSQL port on Docker Desktop.

`bin/flyway.ps1` provides the same behavior for PowerShell and uses a native
Windows bind-mount path, avoiding Git Bash path conversion issues.

`flyway/V1__create_posts.sql` enables `pgcrypto` and creates the `posts` table
with a generated UUID primary key, post content, user ID, and creation/update
timestamps. `flyway/V2__create_post_keywords.sql` creates `post_keywords` with
a generated UUID primary key, keyword, and a foreign key to `posts`. The
keyword/post pair is unique. `flyway/V3__create_post_likes.sql` creates
`post_likes` with a generated UUID primary key, a foreign key to `posts`, a
user ID, and a creation timestamp.

For a database on a different host or port, supply matching settings to both
scripts. For example:

```bash
POSTGRES_PASSWORD=change-me \
POSTGRES_HOST=host.docker.internal \
POSTGRES_PORT=5433 \
POSTGRES_DB=my_posts \
POSTGRES_USER=my_user \
bash bin/flyway.sh
```
