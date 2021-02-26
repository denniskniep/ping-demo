## Start 
How to get devops key and user: https://devops.pingidentity.com/get-started/devopsRegistration/

```
sudo PING_IDENTITY_DEVOPS_USER=<devopsuser> PING_IDENTITY_DEVOPS_KEY=<devopskey> docker-compose up
```

## PingFederate
### Test Auth with SAML
Run the Spring Boot App inside that folder: `samlWithPing/`

```
+-------------------+                 +-------------------+
|                   |    Login via    |                   |
|                   |    SAML         |                   |
|   SamlWithPing    +---------------->+    PingFederate   |
|   Application     |                 |                   |
|                   |                 |                   |
+-------------------+                 +-------------------+
```

http://localhost:8080/
user.0
2FederateM0re


### Test Auth with OIDC
Run the Spring Boot App inside that folder: `oidcWithPing/`

```
+---------------------+                       +--------------------+
|                     |                       |                    |
|                     |       Login via       |                    |
|    OidcWithPing     |       OIDC            |    PingFederate    |
|    Application      +---------------------->+                    |
|                     |                       |                    |
|                     |                       |                    |
+----------+----------+                       +--------------------+
           |
           |
           |
           | AccessToken
           |
           |
           v
+----------+----------+
|                     |
|                     |
|   RessourceServer   |
|     Application     |
|                     |
|                     |
|                     |
+---------------------+
```

http://localhost:8181/
user.0
2FederateM0re

## PingAccess

### Basic Auth
Use hostname host.docker.internal:8383 for access to LegacyWebApp from PingAccess

LegacyWebApp: localhost:8383

Proxy to LegacyWebApp through https://localhost:3000/legacyWebApp/ 
user.0
2FederateM0re

```
                      +----------------+               +----------------+
                      |                |               |                |
                      |                |   Login via   |                |
                      |                |   basic auth  |                |
User  +-------------->+   PingAccess   +-------------->+  LegacyWebApp  |
                      |                |               |                |
                      |                |  Send Header  |                |
                      |                |               |                |
                      +-------+-+------+               +----------------+
                              | ^
                              | |
                              | | Login via OIDC
                              v |
                      +-------+-+------+
                      |                |
                      |                |
                      |                |
                      |  PingFederate  |
                      |                |
                      |                |
                      |                |
                      +----------------+
```

## Setup
```
--------------------------------------------------------------------------------------
- Ping Identity integrated demo
-
-     app    console       login  console           console           rest    ldaps
-     443    9000            9031   9999             6443             1443    1637
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
-   |  PingDirectory        |  https://localhost:6443/   (Server=pingdirectory)      |
-   |  PingDataGovernanceGUI|  https://localhost:8443/                               |
-   |  PingDataGoernance    |  https://localhost:5443/console                        |
-   +-----------------------+--------------------------------------------------------+
-   |  PingDirectory        |  ldaps://localhost:1637                                |
-   |                       |    username: cn=administrator                          |
-   |                       |    password: 2FederateM0re                             |
-   |                       |                                                        |
-   |                       | PingDataGovernanceGUI                                  |
-   |                       |    user id: admin                                      |
-   |                       |    password: password123                               |
-   +-----------------------+--------------------------------------------------------+

--------------------------------------------------------------------------------------
```

## PingAccess ServerProfile
https://devops.pingidentity.com/reference/profileStructures/

### Baseline ServerProfile
Copied from https://github.com/pingidentity/pingidentity-server-profiles/tree/master/baseline/pingaccess to `serverProfiles/pingaccess/`

### Custom ServerProfile
How to maintain custom Profile: https://devops.pingidentity.com/how-to/buildPingFederateProfile/

1. Export Configuration 
```
cd serverProfiles/bulkExportTool/

mkdir out

curl -X GET \
     --user "administrator:2FederateM0re" \
     --header 'Content-Type: application/json' \
     --header 'X-XSRF-Header: PingAccess' \
     --insecure \
     https://localhost:9000/pa-admin-api/v3/config/export > out/pa-export.json

```


2. Replace Secrets, URLS, etc. with EnvironmentVariables
Use https://github.com/pingidentity/pingidentity-devops-getting-started/tree/master/99-helper-scripts/ping-bulkconfigtool
for variablisation

`bulkExportTool/ping-bulkexport-tools.jar`

```
java -jar ./ping-bulkexport-tools.jar ./in/pa-config.json ./out/pa-export.json ./out/pa-env_vars.env ./out/pa-data.json.subst > ./out/pa-export-convert.log
```

Copy pa-data.json.subst to serverProfiles/pingAccess/instance/data/start-up-deployer/
Copy pa-env_vars.env to serverProfiles/pingAccess/

## PingFederate ServerProfile
### Baseline ServerProfile
Copied from https://github.com/pingidentity/pingidentity-server-profiles/tree/master/baseline/pingfederate to `serverProfiles/pingFederate/`

### Custom ServerProfile
How to maintain custom Profile: https://devops.pingidentity.com/how-to/buildPingFederateProfile/

1. Export Configuration 
```
cd serverProfiles/bulkExportTool/

mkdir out

curl \
  --insecure \
  --location \
  --request GET \
  'https://pingfederate:9999/pf-admin-api/v1/bulk/export' \
  --header 'X-XSRF-Header: PingFederate' \
  --user "administrator:2FederateM0re" > ./out/pf-export.json
```

2. Replace Secrets, URLS, etc. with EnvironmentVariables
Use https://github.com/pingidentity/pingidentity-devops-getting-started/tree/master/99-helper-scripts/ping-bulkconfigtool
for variablisation


`bulkExportTool/ping-bulkexport-tools.jar`

```
java -jar ./ping-bulkexport-tools.jar ./in/pf-config.json ./out/pf-export.json ./out/pf-env_vars.env ./out/pf-data.json.subst > ./out/pf-export-convert.log
```

Copy pf-data.json.subst to serverProfiles/pingFederate/instance/bulk-config/
Copy pf-env_vars.env to serverProfiles/pingFederate/


## Solved Issues

Error on RessourceServer:
`Spring boot Signed JWT rejected: Another algorithm expected, or no matching key(s) found`

Key can not be found on PingFederates `/pf/JWKS` Endpoint

There is already a support case:
https://support.pingidentity.com/s/question/0D51W00005t4LzISAU/shared-pfjwks-for-keys-used-for-jwt-access-token-signing

-> Access Token Management > Use Centralized Signing Key