
<h1 align="center"><a href="https://www.meteorite.bi/products/saiku">Kusai Analytics</a></h1>
<h2 align="center">Open source OLAP browser</h2>
<p align="center"><a href="https://www.meteorite.bi/products/saiku"><img src="https://raw.githubusercontent.com/OSBI/saiku/assets/saiku-demo-1.jpg"/></a></p>
<hr />
<p align="center">
  <a href="https://www.meteorite.bi"><b>Homepage</b></a> |
  <a href="https://licensing.meteorite.bi"><b>Saiku License</b></a> |
  <a href="https://saiku-documentation.readthedocs.io/en/latest/"><b>Wiki</b></a> |
  <a href="https://community.meteorite.bi/"><b>Community</b></a> |
  <a href="https://groups.google.com/a/saiku.meteorite.bi/forum/#!forum/dev"><b>Mailing List</b></a> |
  <a href="https://webchat.freenode.net/?randomnick=1&channels=%23%23saiku"><b>Chat</b></a> |
  <a href="https://twitter.com/SaikuAnalytics"><b>News</b></a>
</p>

***

<p align="justify">
  Saiku allows business users to explore complex data sources,
  using a familiar drag and drop interface and easy to understand
  business terminology, all within a browser. Select the data you
  are interested in, look at it from different perspectives,
  drill into the detail. Once you have your answer, save your results,
  share them, export them to Excel or PDF, all straight from the browser.
  <a href="https://www.meteorite.bi">(more)</a>
</p>

***

## Setup

### Build Instructions

```sh
mvn clean install -DskipTests

mvn clean clover2:setup test clover2:aggregate clover2:clover
```

### Update project version

To update the pom versions run:

```sh
mvn versions:set -DnewVersion=3.x.x
```

Then remove the backups with:

```sh
find . -name "*.versionsBackup" -type f -delete
```

## Get Saiku License

Saiku is open source and free to use. Our default server does ship with a license server installed. To get a license you can visit https://licensing.meteorite.bi and get a FREE license which is pinned to the major release of the server. This helps us with a more accurate picture of installation numbers and deployments.

## Wiki

* [Saiku Wiki](https://saiku-documentation.readthedocs.io/en/latest/)

## Community

* [Saiku Community](https://community.meteorite.bi/)

## Bugs and Feature Requests

* [GitHub Issues](https://github.com/OSBI/saiku/issues/new)


## Browser Support

We do care about it.

| ![Edge](https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png) | ![Chrome](https://raw.github.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png) | ![Firefox](https://raw.github.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png) | ![Opera](https://raw.github.com/alrra/browser-logos/master/src/opera/opera_48x48.png) | ![Safari](https://raw.github.com/alrra/browser-logos/master/src/safari/safari_48x48.png) |
| --------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| Latest ✔                                                                                      | Latest ✔                                                                                 | Latest ✔                                                                                    | Latest ✔                                                                              | Latest ✔                                                                                 |


## Contributing

Check [CONTRIBUTING.md](./CONTRIBUTING.md) for more details. Some important information:



## History

For detailed changelog, check [Releases](https://github.com/OSBI/saiku/releases).

## License

Saiku and the Saiku UI are free software. The UI, contained in this repository, is available under the terms of the Apache License Version 2. A copy is attached for your convenience.

**[⬆ back to top](#readme)**
