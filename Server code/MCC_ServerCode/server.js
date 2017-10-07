const express = require('express');
const http = require('http');
const url = require('url');
const opn = require('opn');
const WebSocket = require('ws');
const bodyParser = require('body-parser');
const app = express();
const fs = require('fs');

app.use(express.static('public'));
app.use(bodyParser.urlencoded({extended:false}));
app.use(bodyParser.json())

const server = http.createServer(app);
const wss = new WebSocket.Server({server});
var i = 0;
CLIENTS = {};
userCounter = 1;

wss.on('connection', function connection(ws) {
	CLIENTS[ws.upgradeReq.connection.remoteAddress] = userCounter++;
	const location = url.parse(ws.upgradeReq.url, true);
        ws.on('message', function incoming(message) {
        if(message == 100){    //send me files please
        	console.log("100 got from client");
			var fileName = "./userFolder/bundle.zip";

			fs.exists(fileName, function(exists) {
			  if (exists) {
			    fs.stat(fileName, function(error, stats) {
			      fs.open(fileName, "r", function(error, fd) {
			        var buffer = new Buffer(stats.size);

			        fs.read(fd, buffer, 0, buffer.length, null, function(error, bytesRead, buffer) {
			          //var data = buffer.toString("utf8", 0, buffer.length);
			           ws.send(buffer);
			          //console.log(data);
			          fs.close(fd);
			        });
			      });
			    });
			  }
			});
        	ws.send(100);
        }


        if(message==200){
			console.log("bhaiya kuch na karo");
		}

		else{  // process my list and save files

 			console.log('received: %d %s', ++i, message);
			fs.writeFile('./userFolder/userData.json',message, function(err){
				if(err){
					 console.log(err);
					 return;
				}
				// otherwise file got saved
				console.log("file got saved");

				var spawn = require("child_process").spawn;
				var process = spawn('python',["create_history.py"]);
				process.stdout.on('data', function (data){
					console.log("Python script returned : %s", data)
					console.log(Object.prototype.toString.apply(data));
					ws.send(200);

				});

			});
		}
	});
});

server.listen(8086,"127.0.0.1", function listening(){ // 80,"172.31.9.255"
   console.log('Listening on %s %d',server.address().address, server.address().port);
});
