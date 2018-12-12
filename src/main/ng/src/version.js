//git rev-list --tags --date-order --max-count=1
//git describe --exact-match $(git rev-list --tags --date-order --max-count=1) --tags
'use strict';
const { spawnSync } = require('child_process');
const { resolve, relative } = require('path');
const { writeFileSync } = require('fs-extra');

const hashCmd = spawnSync( 'git', [ 'rev-list','--tags','--date-order','--max-count=1' ] );
const hash = hashCmd.stdout.toString().trim();
const tagCmd = spawnSync( 'git', [ 'describe','--exact-match',hash,'--tags' ] );
const op = {'version':tagCmd.stdout.toString().trim(), 'hash':hash};

const file = resolve(__dirname, '..', 'src', 'environments', 'version.ts');

writeFileSync(file,
`// IMPORTANT: THIS FILE IS AUTO GENERATED! DO NOT MANUALLY EDIT OR CHECKIN!
/* tslint:disable */
export const VERSION = ${JSON.stringify(op, null, 2)};
/* tslint:enable */
`, { encoding: 'utf-8' });

console.log(`Wrote ${JSON.stringify(op)} to ${relative(resolve(__dirname, '..'), file)}`);
if(hashCmd.stderr.toString()){
    console.log(`hashError: ${hashCmd.stderr.toString()}`);
}
if(tagCmd.stderr.toString()){
    console.log(`tagError: ${tagCmd.stderr.toString()}`);
}