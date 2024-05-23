# lab8
## Client-Server application for collection managment
### Client-Server network application written using only Java Core

**Client**
1) UI written using JavaFX
2) For internationalization and localization I used i18n and l10n
3) For styling used CSS

**Server**
1) Transport protocol - UDP
2) Multitreading<sub>1</sub>:fixed thread pool for request processing
3) Multitreading<sub>2</sub>:Fork join pool for respoonce sending
4) Multitreading<sub>3</sub>:Read-write locks for collection safety

**Client-Server interaction**
1) Transport protocole - UDP
2) Sending special classes for request and response, not just strings
3) Chunks splitting for large packages
