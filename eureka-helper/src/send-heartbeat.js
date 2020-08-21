const axios = require("axios").default;
const logger = require("./logger");
const helpers = require("./helpers");
const _ = require("lodash");

module.exports = (eureka, appName, port, options = {}) => {
  const HEARTBEAT_INTERVAL_MS = _.get(options, "heartBeatIntervalMS", 5);

  const intervalId = setInterval(async () => {
    const eurekaUrl = new String(eureka)
      .concat("/apps/")
      .concat(appName)
      .concat(
        `/${_.get(options, "instanceId", helpers.getInstanceId(appName, port))}`
      );

    try {
      logger.info("Sending Heartbeat to Eureka!");

      await axios.put(eurekaUrl);

      logger.info("Sending Heartbeat to Eureka Success!");
    } catch (error) {
      logger.error("Eureka Heartbeat Error: ", error);
    }
  }, HEARTBEAT_INTERVAL_MS * 1000);
};
