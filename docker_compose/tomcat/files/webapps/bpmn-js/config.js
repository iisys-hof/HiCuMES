const CONFIG = {

    DEPLOYMENT_SOURCE: 'Modeler',

    BPMN_EDITOR_URL: 'http://localhost:9080/bpmn-js',
    CMMN_EDITOR_URL: 'http://localhost:9080/cmmn-js',
    DMN_EDITOR_URL: 'http://localhost:9080/dmn-js',

    CAMUNDA_REST_URL: 'http://localhost:9080/engine-rest',
    REST_DEPLOY_CREATE: '/deployment/create',
    REST_XML_SUFFIX: '/xml/',
    REST_VERSION_SUFFIX: '?version=',
    REST_VERSIONTAG_SUFFIX: '?versionTag=',

    //BPMN
    REST_GET_PROCESS_DEF_WITH_ID: '/process-definition/',
    REST_GET_PROCESS_DEF_WITH_KEY: '/process-definition/key/',
    REST_GET_PROCESS_DEFS_LASTEST: '/process-definition?latestVersion=true',
    REST_GET_PROCESS_DEFS_ALL: '/process-definition?sortBy=version&sortOrder=desc',

    //CMMN
    REST_GET_CASE_DEF_WITH_ID: /case-definition/,
    REST_GET_CASE_DEF_WITH_KEY: '/case-definition/key/',
    REST_GET_CASE_DEFS_LATEST: '/case-definition?latestVersion=true',
    REST_GET_CASE_DEFS_ALL: '/case-definition?sortBy=version&sortOrder=desc',

    //DMN
    REST_GET_DECISION_DEF_WITH_ID: /decision-definition/,
    REST_GET_DECISION_DEF_WITH_KEY: '/decision-definition/key/',
    REST_GET_DECISION_DEFS_LATEST: '/decision-definition?latestVersion=true',
    REST_GET_DECISION_DEFS_ALL: '/decision-definition?sortBy=version&sortOrder=desc'

};

window.CONFIG = CONFIG;
