const { Service } = require("feathers-mongoose");
const { GeneralError } = require("@feathersjs/errors");

const axios = require("axios").default;
const uuid = require("uuid").v4;

exports.UserOrders = class UserOrders extends Service {
  constructor(options) {
    super(options);
    this.app = options.app;
    this.zuul = this.app.get("zuul");
  }

  async create(data, params) {
    try {
      const orderData = {
        ...data,
        orderId: uuid(),
      };
      const response = await super.create(orderData, params);

      return response;
    } catch (error) {
      throw new GeneralError(error);
    }
  }

  async get(id, params) {
    const order = await super.get(id, params);
    const user = await axios.get(`${this.zuul}/user/users/${order.userId}`, {
      headers: {
        Authorization: params.headers.authorization,
      },
    });

    order.user = user.data;

    return order;
  }

  async find(params) {
    const userOrders = await super.find({ ...params, paginate: false });

    for (const userOrder of userOrders) {
      const user = await axios.get(
        `${this.zuul}/user/users/${userOrder.userId}`,
        {
          headers: {
            Authorization: params.headers.authorization,
          },
        }
      );

      userOrder.user = user.data;
    }

    return userOrders;
  }
};
