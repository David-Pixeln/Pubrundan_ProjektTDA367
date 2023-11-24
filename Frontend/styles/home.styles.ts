import { CustomTheme } from '@constants/themes';
import { StyleSheet } from 'react-native';



export const HomePageStyles = (theme: CustomTheme) => StyleSheet.create({
  pageContainer: {
    flex: 1,
    backgroundColor: theme.colors.background.s200,
  },
  cardContainer: {
    paddingHorizontal: 20,
    
    display: 'flex',
    flex: 1,
    gap: 20,
  },
  button: {
    width: 52,
    aspectRatio: 1,
    
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',

    borderRadius: 50,
    backgroundColor: theme.colors.background.s600,

    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 12,
    },
    shadowOpacity: 0.58,
    shadowRadius: 16.00,
    
    elevation: 24,
  },
  buttonPressed: {
    backgroundColor: theme.colors.background.s200,
  },
});