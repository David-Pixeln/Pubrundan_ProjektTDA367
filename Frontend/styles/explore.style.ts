import { CustomTheme } from '@constants/themes';
import { StyleSheet } from 'react-native';



export const ExplorePageStyles = (theme: CustomTheme) => StyleSheet.create({
  container: {
    flex: 1,
  },
  map: {
    width: '100%',
    height: '100%',
  }
});