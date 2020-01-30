# git-peek

Get a peek at repo information using Github API.

## Endpoints

Find programming languages used in repo:

`/shopify/repo-languages?repo=$REPONAME`

Find all comments for a repo for a given year

`/shopify/repo-comments?repo=$REPONAME?year=$YEAR`

Find the most recent repos for an organisation

`/shopify/org-repo-recents?org=$ORGNAME?amount=$AMOUNT`


## Build and run