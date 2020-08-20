/* eslint-disable no-unused-vars */
exports.DecodeToken = class DecodeToken {
  constructor(options) {
    this.options = options || {};
  }

  async find(params) {
    const { user: currentUser } = params;

    delete currentUser.password;

    return currentUser;
  }
};
