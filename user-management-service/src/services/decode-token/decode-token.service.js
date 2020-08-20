// Initializes the `decode-token` service on path `/decode-token`
const { DecodeToken } = require('./decode-token.class');
const hooks = require('./decode-token.hooks');

module.exports = function (app) {
  const options = {
    paginate: app.get('paginate')
  };

  // Initialize our service with any options it requires
  app.use('/decode-token', new DecodeToken(options, app));

  // Get our initialized service so that we can register hooks
  const service = app.service('decode-token');

  service.hooks(hooks);
};
