// Initializes the `user-orders` service on path `/user-orders`
const { UserOrders } = require("./user-orders.class");
const createModel = require("../../models/user-orders.model");
const hooks = require("./user-orders.hooks");

module.exports = function (app) {
  const options = {
    Model: createModel(app),
    paginate: app.get("paginate"),
    app,
  };

  // Initialize our service with any options it requires
  app.use("/user-orders", new UserOrders(options, app));

  // Get our initialized service so that we can register hooks
  const service = app.service("user-orders");

  service.hooks(hooks);
};
