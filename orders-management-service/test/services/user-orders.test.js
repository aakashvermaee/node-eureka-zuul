const assert = require('assert');
const app = require('../../src/app');

describe('\'user-orders\' service', () => {
  it('registered the service', () => {
    const service = app.service('user-orders');

    assert.ok(service, 'Registered the service');
  });
});
