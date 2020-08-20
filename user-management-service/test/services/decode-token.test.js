const assert = require('assert');
const app = require('../../src/app');

describe('\'decode-token\' service', () => {
  it('registered the service', () => {
    const service = app.service('decode-token');

    assert.ok(service, 'Registered the service');
  });
});
