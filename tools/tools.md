Tools
===========

# jq

http://webgeodatavore.com/jq-json-manipulation-command-line-with-geojson.html

Examples make use of: stacks.json.gz

Find all stackItems which have an itemType of NETWORK and where networkId is null and pack them in an array called items.

```bash
jq '{items: [ .stackItems[].items[] | select(  .itemType == "NETWORK") | select( .networkId == null )]}'
```