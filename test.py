import os
from collections import defaultdict
from tqdm import tqdm

def get_all_files(root):
    file_list = []
    for dirpath, _, filenames in os.walk(root):
        for file in filenames:
            file_list.append(os.path.join(dirpath, file))
    return file_list

def sizeof_fmt(num, suffix="Б"):
    for unit in ['','К','М','Г','Т']:
        if abs(num) < 1024.0:
            return f"{num:.1f} {unit}{suffix}"
        num /= 1024.0
    return f"{num:.1f} П{suffix}"

def progress_bar(value, max_value, width=30):
    filled = int(width * value / max_value)
    bar = "█" * filled + " " * (width - filled)
    return f"[{bar}]"

def analyze_by_extension(root="."):
    files = get_all_files(root)
    ext_sizes = defaultdict(int)
    heavy_files = []

    for file in tqdm(files, desc="Анализ файлов", unit="файл"):
        try:
            size = os.path.getsize(file)
            ext = os.path.splitext(file)[1].lower() or "без расширения"
            ext_sizes[ext] += size
            heavy_files.append((file, size))
        except Exception:
            continue

    # Топ-10 тяжёлых файлов
    heavy_files.sort(key=lambda x: -x[1])
    with open("heavy_files.log", "w", encoding="utf-8") as log_file:
        log_file.write("Топ 10 самых тяжёлых файлов:\n")
        for path, size in heavy_files[:10]:
            log_file.write(f"{sizeof_fmt(size)} - {path}\n")

    # Вывод по расширениям с прогресс-барами
    print("\n-----------------------")
    max_size = max(ext_sizes.values(), default=1)
    for ext, total_size in sorted(ext_sizes.items(), key=lambda x: -x[1]):
        bar = progress_bar(total_size, max_size)
        print(f"{ext:10} {bar} {sizeof_fmt(total_size)}")
    print("-----------------------")
    print("Лог самых тяжёлых файлов сохранён в heavy_files.log")

if __name__ == "__main__":
    analyze_by_extension(".")  # Можно указать путь, например "D:/Games"
