const users = require('./users/users.service.js');
const decodeToken = require('./decode-token/decode-token.service.js');
// eslint-disable-next-line no-unused-vars
module.exports = function (app) {
  app.configure(users);
  app.configure(decodeToken);
};
