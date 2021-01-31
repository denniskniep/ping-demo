## Start 
How to get devops key and user: https://devops.pingidentity.com/get-started/devopsRegistration/

```
sudo PING_IDENTITY_DEVOPS_USER=<devopsuser> PING_IDENTITY_DEVOPS_KEY=<devopskey> docker-compose up
```

## Test Auth with SAML
Run the Spring Boot App inside that folder: `samlWithPing/`


## Test Auth with OIDC
Run the Spring Boot App inside that folder: `oidcWithPing/`


## PingFederate ServerProfile
### Baseline ServerProfile
Copied from https://github.com/pingidentity/pingidentity-server-profiles/tree/master/baseline/pingfederate to serverProfiles/pingFederate/

### Custom ServerProfile
How to maintain custom Profile: https://devops.pingidentity.com/how-to/buildPingFederateProfile/

1. Export Configuration 
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

2. Replace Secrets, URLS, etc. with EnvironmentVariables
Use https://github.com/pingidentity/pingidentity-devops-getting-started/tree/master/99-helper-scripts/ping-bulkconfigtool
for variablisation


`bulkExportTool/ping-bulkexport-tools.jar`

```
java -jar ./ping-bulkexport-tools.jar ./in/pf-config.json ./out/data.json ./out/env_vars.env ./out/data.json.subst > ./out/export-convert.log
```