


## Start 
How to get devops key and user: https://devops.pingidentity.com/get-started/devopsRegistration/

```
sudo PING_IDENTITY_DEVOPS_USER=<devopsuser> PING_IDENTITY_DEVOPS_KEY=<devopskey> docker-compose up
```

## Test Auth with SAML
Run the Spring Boot App inside that folder: `samlWithPing/`


## Test Auth with OIDC
Run the Spring Boot App inside that folder: `oidcWithPing/`


## Setup
```
--------------------------------------------------------------------------------------
- Ping Identity integrated demo
-
-     app    console       login  console           console           rest    ldaps
-     443    9000            9031   9999             8443             1443    1637
-      |      |               |      |                 |                    |   
-   +----------------+    +---------------+    +---------------+    +---------------+
-   |   PingAccess   |    | PingFederate  |    |PingDataConsole|    | PingDirectory |
-   +----------------+    +---------------+    +---------------+    +---------------+ 
-
-
-   +-----------------------+--------------------------------------------------------+
-   |  Product Console/App  |  URL                                                   |
-   |                       |    username: administrator                             |
-   |                       |    password: 2FederateM0re                             |
-   +-----------------------+--------------------------------------------------------+
-   |  PingAccess           |  https://localhost:9000/                               |
-   |  PingFederate         |  https://localhost:9999/pingfederate/app               |
-   |  PingDirectory        |  https://localhost:8443/   (Server=pingdirectory)      |
-   +-----------------------+--------------------------------------------------------+
-   |  PingDirectory        |  ldaps://localhost:1637                                |
-   |                       |    username: cn=administrator                          |
-   |                       |    password: 2FederateM0re                             |
-   +-----------------------+--------------------------------------------------------+
--------------------------------------------------------------------------------------
```

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

## Issues

Error on RessourceServer:
`Spring boot Signed JWT rejected: Another algorithm expected, or no matching key(s) found`

Key can not be found on PingFederates `/pf/JWKS` Endpoint

There is already a support case:
https://support.pingidentity.com/s/question/0D51W00005t4LzISAU/shared-pfjwks-for-keys-used-for-jwt-access-token-signing

-> Access Token Management > Use Centralized Signing Key