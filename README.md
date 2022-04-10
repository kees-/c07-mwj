# c07-mwj

The MWJ website. Built with [CLJS](https://clojurescript.org/), [shadow-cljs](https://github.com/thheller/shadow-cljs), and [re-frame](https://github.com/day8/re-frame).

### Development

Run `npm run watch`. Connect to the REPL if desired and visit `http://localhost:8280/` in browser.

### Release

Pushing to main will do one of three things. Two locations are checked, the source directory `src/**` and the static directory `resources/public/**`.

1. If neither location has changes, the deploy action will not run.
2. If the source and/or source and static files are changed, the source will be compiled with `npm run release` (from shadow-cljs). The build output will be synced to S3, and the CloudFront dist is invalidated from root (`'/*'`). Deleted files will be removed.
3. If the static files but not the source is changed, the compile task will be skipped yet S3-deploy still proceeds. Deleted files **will not** be removed, for lack of compiled js.

Skip the action by adding `[no ci]` or similar to the commit message.
