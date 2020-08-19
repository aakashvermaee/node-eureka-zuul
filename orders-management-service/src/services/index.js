const userOrders = require('./user-orders/user-orders.service.js');
// eslint-disable-next-line no-unused-vars
module.exports = function (app) {
  app.configure(userOrders);
};
