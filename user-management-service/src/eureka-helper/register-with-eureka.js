const axios = require('axios').default;
const ip = require('ip');
const logger = require('../logger');

async function regiterWithEureka(eureka, appName, port, instanceId) {
  const eurekaUrl = new String(eureka).concat('/apps/').concat(appName);

  logger.info(`Registering with ${appName} Eureka`);

  try {
    const eurekaResponse = await axios.post(
      eurekaUrl,
      {
        instance: {
          hostName: 'localhost',
          app: `${appName}`.toUpperCase(),
          vipAddress: appName,
          instanceId,
          ipAddr: ip.address(),
          status: 'UP',
          port: {
            $: port,
            '@enabled': true,
          },
          dataCenterInfo: {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
          },
        },
      },
      {
        headers: {
          'content-type': 'application/json',
        },
      }
    );

    if (eurekaResponse) {
      logger.info('Registered With Eureka');
      return true;
    }
  } catch (error) {
    logger.debug('eureka error: ', error);
  }
}

module.exports = regiterWithEureka;
