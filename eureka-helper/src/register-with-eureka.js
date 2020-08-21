const axios = require("axios").default;
const ip = require("ip");
const logger = require("./logger");
const _ = require("lodash");
const helpers = require("./helpers");
const sendHeartbeat = require("./send-heartbeat");

async function regiterWithEureka(eureka, appName, port, options = {}) {
  const eurekaUrl = new String(eureka).concat("/apps/").concat(appName);

  logger.info(`Registering ${appName} with Eureka`);

  try {
    const eurekaResponse = await axios.post(
      eurekaUrl,
      {
        instance: {
          app: _.toUpper(appName),
          vipAddress: appName,
          ipAddr: ip.address(),
          hostName: _.get(options, "hostName", "localhost"),
          instanceId: _.get(
            options,
            "instanceId",
            helpers.getInstanceId(appName, port)
          ),
          status: _.get(options, "status", "UP"),
          port: {
            $: port,
            "@enabled": true,
          },
          dataCenterInfo: {
            "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
            name: "MyOwn",
          },
        },
      },
      {
        headers: {
          "content-type": "application/json",
        },
      }
    );

    if (eurekaResponse) {
      logger.info("Registered With Eureka");

      sendHeartbeat(eureka, appName, port, {});

      return true;
    }
  } catch (error) {
    logger.debug("eureka error: ", error);
  }
}

module.exports = regiterWithEureka;
