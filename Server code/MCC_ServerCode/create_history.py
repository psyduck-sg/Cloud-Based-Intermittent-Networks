import json
import urllib2
import zipfile
import sys



def get_top_urls(in_json):
	out_urls = {}
	data = []
	jsons = []
	jsons = in_json.split('\n')
	for line in jsons:
		if len(line) == 0:
			break
		url_info = json.loads(line)
		data.append([url_info['url'], url_info['datetime'], url_info['visits'],url_info['title']])
	data.sort(key=lambda x: x[2], reverse=True)
	out_urls['url_1.html'] = data[0][0]
	out_urls['url_2.html'] = data[1][0]
	out_urls['url_3.html'] = data[2][0]
	title_list = []
	title_list.append(data[0][3])
	title_list.append(data[1][3])
	title_list.append(data[2][3])
	return out_urls,title_list



def compressAndStore():
	zf = zipfile.ZipFile('./userFolder/bundle.zip', mode='w')
	try:
		zf.write('./userFolder/url_1.html')
		zf.write('./userFolder/url_2.html')
		zf.write('./userFolder/url_3.html')
	finally:
		zf.close()



def main():
	urlList,titles = get_top_urls(open('./userFolder/userData.json').read())
	for key in urlList:
		response = urllib2.urlopen(urlList[key])
		webContent = response.read()
		f = open('./userFolder/'+key, 'w')
		f.write(webContent)
		f.close
		compressAndStore()
	output_str = "___".join(titles)
	print output_str



if __name__ == "__main__":
	main()
