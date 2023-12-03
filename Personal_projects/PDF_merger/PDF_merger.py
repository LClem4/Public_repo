from PyPDF2 import PdfMerger
from tkinter import *
from tkinter.filedialog import askopenfilename

# NOTE: PROGRAM MIGHT STILL RUN IN BACKGROUND AFTER CLOSING

# Mergers PDFS
def pdf_merger(window,pdfs):
    merger = PdfMerger()

    for pdf in pdfs:
        merger.append(pdf)

    merger.write("merged.pdf")
    merger.close()

    T = Label(window, text = "Merged!")
    T.grid(row = 4, column = 2)

# Searches for files
def file_search():
    Tk().withdraw()
    filename = askopenfilename()
    return filename

# Adds pdfs to next slot
def add_pdf_slot(window,row,text):
    FileLabel = Label(window,text=text)
    FileLabel.grid(row = row, column = 1)

# Main function with GUI
def window_GUI():
    window = Tk()
    window.geometry("1000x200") # Window
    pdfs = []
    row = 0

    # Add file
    def add_file():
        nonlocal row
        file = file_search()
        add_pdf_slot(window,row,file)
        row += 1
        pdfs.append(file)

    # Buttons
    addButton = Button(window, text = "add file", command= lambda: add_file())
    addButton.grid(row = 1, column = 2)
    mergeButton = Button(window, text = "merge", command= lambda: pdf_merger(window,pdfs))
    mergeButton.grid(row = 2, column = 2)
    exitButton = Button(window, text="Exit", command=window.destroy)
    exitButton.grid(row = 3, column = 2) 
       
    window.mainloop()
    
# Running main funct
window_GUI()