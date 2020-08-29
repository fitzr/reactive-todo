
const response = (res, status, body) => {
    res.writeHead(status, { 'Content-Type' : 'application/json' })
    res.write(JSON.stringify(body))
    res.end()
}

require('http')
    .createServer((req, res) => {
        if (req.url === '/auth' && req.method === 'POST') {
            let data = ''
            req.on('data', chunk => { data += chunk }).on('end', () => {
                if (data === 'token=Buzz') {
                    response(res, 200, { id: 'BuzzId', name: 'Buzz' })
                } else {
                    response(res, 401, { msg: 'unauthorized'})
                }
            })
        } else {
            response(res, 404, { msg: 'not found'})
        }
    }).listen(3000)

