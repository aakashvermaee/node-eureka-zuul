/* eslint-disable no-console */
const logger = require('./logger');
const app = require('./app');
const port = app.get('port');
const server = app.listen(port);

const { registerWithEureka } = require('eureka-helper');
const { name: appName } = require('../package.json');

process.on('unhandledRejection', (reason, p) =>
  logger.error('Unhandled Rejection at: Promise ', p, reason)
);

server.on('listening', async () => {
  logger.info('Feathers application started on http://%s:%d', app.get('host'), port);

  const eurekaUrl = app.get('eureka');

  await registerWithEureka(eurekaUrl, appName, port, {});
});
