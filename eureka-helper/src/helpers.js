const ip = require('ip');

exports.getInstanceId = (appName, port) => {
  return `${ip.address()}:${appName}:${port}`;
};
