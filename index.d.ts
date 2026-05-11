declare module "react-native-pdf-text-extractor" {
  export function isPasswordProtected(file: string): Promise<boolean>;
  export function extractPdfText(
    file: string,
    password?: string,
  ): Promise<string>;

  const PdfTextExtractor: {
    isPasswordProtected: typeof isPasswordProtected;
    extractPdfText: typeof extractPdfText;
  };

  export default PdfTextExtractor;
}
