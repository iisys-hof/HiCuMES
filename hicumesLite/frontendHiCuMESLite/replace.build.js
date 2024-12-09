var replace = require('replace-in-file');
const moment = require("moment");
var timeStamp = new Date();
var execSync = require('child_process').execSync;

try {
  // Get the latest commit hash
  var commitHash = execSync('git rev-parse --short HEAD').toString().trim();

  const options = {
    files: [
      'src/build.ts'
    ],
    from: [
      /buildTimestamp: '(.*)'/g,
      /commitHash: '(.*)'/g
    ],
    to: [
      "buildTimestamp: '" + moment(timeStamp).format("DD.MM. HH:mm") + "'",
      "commitHash: '" + commitHash + "'"
    ],
    allowEmptyPaths: false,
  };

  let changedFiles = replace.sync(options);
  if (changedFiles.length == 0) {
    throw "Please make sure that the file '" + options.files + "' contains \"buildTimestamp: ''\" and \"commitHash: ''\"";
  }
  console.log('Build timestamp is set to: ' + timeStamp);
  console.log('Commit hash is set to: ' + commitHash);
} catch (error) {
  console.error('Error occurred:', error);
  throw error;
}
