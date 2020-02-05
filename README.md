# git-peek

Get a peek at repo information using Github API.

## Endpoints

Find programming languages used in repo:

`/shopify/repo-languages?repo=$REPONAME`

Find all comments for a repo for a given year

`/shopify/repo-comments?repo=$REPONAME&year=$YEAR`

Find the most recent repos for an organisation

`/shopify/org-repo-recents?org=$ORGNAME&amount=$AMOUNT`


## Build and run

There's three methods available for running this app: Leiningen, Java or Docker.

For Leiningen simply `lein run` and you're in business.

For Java you will need the standalone jar. This is compiled with `lein uberjar`
and subsequently run with `java -jar target/uberjar/git-peek-0.1.0-SNAPSHOT-standalone.jar`

Easiest is building and running a docker image. For this build the container with `docker build -t rest-app .`
and run with `docker run -p 3000:3000 rest-app`
