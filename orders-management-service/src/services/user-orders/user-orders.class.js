const { Service } = require("feathers-mongoose");
const { GeneralError } = require("@feathersjs/errors");

const uuid = require("uuid").v4;

exports.UserOrders = class UserOrders extends Service {
  constructor(options) {
    super(options);
    this.app = options.app;
    this.zuul = this.app.get("zuul");
  }

  async create(data, params) {
    try {
      console.log("$$$ data:", data);

      const orderData = {
        ...data,
        userId: data.userid || params.headers.userid,
        orderId: uuid(),
      };
      const response = await super.create(orderData, params);

      return response;
    } catch (error) {
      throw new GeneralError(error);
    }
  }

  async find(params) {
    const userOrders = await super.find({
      ...params,
      query: {
        ...params.query,
        userId: params.headers.userid,
      },
    });

    return userOrders;
  }
};
