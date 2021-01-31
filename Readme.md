

## PingFederate Serverprofile
### Baseline
Copied from https://github.com/pingidentity/pingidentity-server-profiles/tree/master/baseline/pingfederate to serverProfiles/pingFederate/

### Custom Profile
How to update Profile: https://devops.pingidentity.com/how-to/buildPingFederateProfile/

```
cd serverProfiles/bulkExportTool/

mkdir out

curl \
  --insecure \
  --location \
  --request GET 'https://pingfederate:9999/pf-admin-api/v1/bulk/export' \
  --header 'X-XSRF-Header: PingFederate' \
  --user "administrator:2FederateM0re" > ./out/data.json
```

Use https://github.com/pingidentity/pingidentity-devops-getting-started/tree/master/99-helper-scripts/ping-bulkconfigtool
for variablisation


`bulkExportTool/ping-bulkexport-tools.jar`

```
java -jar ./ping-bulkexport-tools.jar ./in/pf-config.json ./out/data.json ./out/env_vars.env ./out/data.json.subst > ./out/export-convert.log
```