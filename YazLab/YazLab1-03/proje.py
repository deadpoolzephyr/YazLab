# import PyPDF3
 
# pdfFileObject = open(r"bir.pdf", 'rb')
# f = open("output.txt", "w")

# pdfReader = PyPDF3.PdfFileReader(pdfFileObject)
 
# print(" No. Of Pages :", pdfReader.numPages)
 
# pageObject = pdfReader.getPage(0)

# f.write(pageObject.extractText())

# pdfFileObject.close()
# f.close()


import PyPDF2
pdffile = open('PDFler/bir.pdf', 'rb')
f = open("output.txt", "w")
pdfReader = PyPDF2.PdfFileReader(pdffile)
count = 1 + pdfReader.numPages
for i in range(count):
    page = pdfReader.getPage(i)
    f.write(page.extractText())
    print(page.extractText())