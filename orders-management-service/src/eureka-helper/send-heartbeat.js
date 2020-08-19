const axios = require("axios").default;
const logger = require("../logger");

module.exports = (eureka, appName, instanceId) => {
  setInterval(async () => {
    const eurekaUrl = new String(eureka)
      .concat("/apps/")
      .concat(appName)
      .concat(`/${instanceId}`);

    try {
      logger.info("Sending Heartbeat to Eureka!");

      await axios.put(eurekaUrl);

      logger.info("Sending Heartbeat to Eureka Success!");
    } catch (error) {
      logger.error("Eureka Heartbeat Error: ", error);
    }
  }, 5 * 1000);
};
