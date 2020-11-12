function debug(msg) {
  const mode = 'pro'
  if(mode == 'debug') {
    console.log(msg)
  }
}

module.exports = {
  debug: debug
}